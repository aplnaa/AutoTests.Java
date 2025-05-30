package requests.noauth;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;

public class RequestPostRestful {
    private String name;
    private Data data;

    public RequestPostRestful(String name, Data data){
        this.name = name;
        this.data = data;
    }


    public static class Data{
        private Integer year;
        private Double price;

        @JsonProperty("CPU model")
        private String cpuModel;

        @JsonProperty("Hard disk size")
        private String hardDiskSize;

        public Data(Integer year, Double price, String cpuModel, String hardDiskSize){
            this.year = year;
            this.price = price;
            this.cpuModel = cpuModel;
            this.hardDiskSize = hardDiskSize;
        }
    }
}
