package dto;

import com.google.gson.annotations.SerializedName;

public class ConversionResponseDTO {

    private String  result = null;

    private String  documentation = null;

    @SerializedName("terms_of_use")
    private String  termsOfUse = null;

    @SerializedName("time_last_update_unix")
    private Integer timeLastUpdateUnix = null;

    @SerializedName("time_last_update_utc")
    private String  timeLastUpdateUtc = null;

    @SerializedName("time_next_update_unix")
    private Integer timeNextUpdateUnix = null;

    @SerializedName("time_next_update_utc")
    private String  timeNextUpdateUtc = null;

    @SerializedName("base_code")
    private String  baseCode = null;

    @SerializedName("target_code")
    private String  targetCode = null;

    @SerializedName("conversion_rate")
    private Double  conversionRate = null;

    @SerializedName("conversion_result")
    private Double  conversionResult = null;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public Integer getTimeLastUpdateUnix() {
        return timeLastUpdateUnix;
    }

    public String getTimeLastUpdateUtc() {
        return timeLastUpdateUtc;
    }

    public Integer getTimeNextUpdateUnix() {
        return timeNextUpdateUnix;
    }

    public String getTimeNextUpdateUtc() {
        return timeNextUpdateUtc;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public Double getConversionResult() {
        return conversionResult;
    }
}
