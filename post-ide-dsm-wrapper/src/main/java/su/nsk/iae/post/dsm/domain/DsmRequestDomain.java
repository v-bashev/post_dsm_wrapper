package su.nsk.iae.post.dsm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DsmRequestDomain {
    private String id;
    private String root;
    private String fileName;
    private String ast;
}