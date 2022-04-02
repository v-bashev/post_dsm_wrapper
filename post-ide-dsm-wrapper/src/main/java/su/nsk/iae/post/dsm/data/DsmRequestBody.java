package su.nsk.iae.post.dsm.data;

public class DsmRequestBody {
    private String id;
    private String root;
    private String fileName;
    private String ast;

    public String getId() {
        return id;
    }

    public String getRoot() {
        return root;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAst() {
        return ast;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }
}