package dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodeResponseDTO {

    @SerializedName("result")
    private String result = null;

    @SerializedName("documentation")
    private String documentation = null;

    @SerializedName("terms_of_use")
    private String termsOfUse = null;

    @SerializedName("supported_codes")
    private List<String[]> supportedCodes = null;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public List<String[]> getSupportedCodes() {
        return supportedCodes;
    }
}
