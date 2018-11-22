package engine.display;

public class Animation {
    private String id;
    private Integer startFrame, endFrame;

    public Animation(String id, Integer startFrame, Integer endFrame) {
        this.id = id;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public String getId() {
        return this.id;
    }
    public Integer getStartFrame() {
        return this.startFrame;
    }
    public Integer getEndFrame() {
        return this.endFrame;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setStartFrame(Integer startFrame) {
        this.startFrame = startFrame;
    }
    public void setEndFrame(Integer endFrame) {
        this.endFrame = endFrame;
    }
}
