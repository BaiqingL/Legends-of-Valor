package RPG.World;

public class Tile {
    private String content;

    public Tile(String content) {
        switch (content) {
            case "hero":
                this.content = "H";
                break;
            case "market":
                this.content = "M";
                break;
            case "wild":
                this.content = "W";
                break;
            case "blocked":
                this.content = "X";
                break;
            case "enemy":
                this.content = "E";
                break;
            default:
                this.content = " ";
                break;
        }
    }

    public String getContent() {
        return content;
    }
}
