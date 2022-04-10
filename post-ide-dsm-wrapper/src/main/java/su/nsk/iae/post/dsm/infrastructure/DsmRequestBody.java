package su.nsk.iae.post.dsm.infrastructure;

import lombok.Data;
import su.nsk.iae.post.dsm.domain.DsmRequestDomain;

@Data
public class DsmRequestBody {
    private String id;
    private String root;
    private String fileName;
    private String ast;

    public DsmRequestDomain toDomain() {
        return new DsmRequestDomain(id, root, fileName, ast);
    }
}