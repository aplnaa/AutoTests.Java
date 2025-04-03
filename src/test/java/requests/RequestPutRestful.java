package requests;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;


public class RequestPutRestful {
    private String name;
    private Data data;

    public RequestPutRestful(String name, Data data){
        this.name = name;
        this.data = data;
    }


    public static class Data{
        private Integer year;
        private Double price;
        private String color;

        @JsonProperty("CPU model")
        private String cpuModel;

        @JsonProperty("Hard disk size")
        private String hardDiskSize;

        public Data(Integer year, Double price, String color, String cpuModel, String hardDiskSize){
            this.year = year;
            this.price = price;
            this.color = color;
            this.cpuModel = cpuModel;
            this.hardDiskSize = hardDiskSize;
        }
    }
}
