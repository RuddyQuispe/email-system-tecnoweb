package edu.bo.uagrm.ficct.inf513.services;

import java.util.List;

import edu.bo.uagrm.ficct.inf513.utils.TokenAction;
import edu.bo.uagrm.ficct.inf513.utils.TokenUseCase;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-10 22:49
 */
public class Core {
    private String useCase;
    private String action;
    private List<String> parameters;

    public Core(String useCase, String action, List<String> parameters) {
        this.useCase = useCase;
        this.action = action;
        this.parameters = parameters;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getUseCase() {
        return this.useCase;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
