interface Playable {
    void play();
}

interface Compressible {
    int compress(); // 回傳壓縮後大小（KB）
}

abstract class MediaFile {
    private String filename;
    private int    sizeKb;

    MediaFile(String filename, int sizeKb) {
        this.filename = filename;
        this.sizeKb   = Math.max(0, sizeKb);
    }

    String getFilename() { return filename; }
    int    getSizeKb()   { return sizeKb;   }

    abstract String fileType();

    @Override
    public String toString() {
        return fileType() + ":" + filename + "(" + sizeKb + "KB)";
    }
}

class ImageFile extends MediaFile implements Compressible {
    ImageFile(String filename, int sizeKb) { super(filename, sizeKb); }

    @Override
    public String fileType() { return "Image"; }

    @Override
    public int compress() {
        int result = getSizeKb() * 3 / 10; // 壓縮到 30%
        System.out.println("[Compress] " + getFilename() + " -> " + result + "KB");
        return result;
    }
}

class AudioFile extends MediaFile implements Playable {
    AudioFile(String filename, int sizeKb) { super(filename, sizeKb); }

    @Override
    public String fileType() { return "Audio"; }

    @Override
    public void play() {
        System.out.println("[Play Audio] " + getFilename());
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private String resolution;

    VideoFile(String filename, int sizeKb, String resolution) {
        super(filename, sizeKb);
        this.resolution = resolution;
    }

    @Override
    public String fileType() { return "Video"; }

    @Override
    public void play() {
        System.out.println("[Play Video] " + getFilename() + " @ " + resolution);
    }

    @Override
    public int compress() {
        int result = getSizeKb() / 2;
        System.out.println("[Compress] " + getFilename() + " -> " + result + "KB");
        return result;
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] files = {
            new ImageFile("photo.jpg",   2048),
            new AudioFile("song.mp3",     8192),
            new VideoFile("movie.mp4", 204800, "1080p"),
            new ImageFile("icon.png",      64),
        };

        System.out.println("=== 媒體檔案清單 ===");
        for (MediaFile f : files) {
            System.out.print(f + " | ");
            if (f instanceof Playable p)     { p.play(); }
            if (f instanceof Compressible c) { c.compress(); }
            if (!(f instanceof Playable) && !(f instanceof Compressible)) {
                System.out.println("（僅儲存）");
            }
        }
    }
}