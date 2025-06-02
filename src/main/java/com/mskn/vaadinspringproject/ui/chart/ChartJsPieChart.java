package com.mskn.vaadinspringproject.ui.chart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import elemental.json.Json;

import java.util.Map;

@Tag("chart-js-pie")
@JsModule("./src/chart-js-pie.js")
public class ChartJsPieChart extends Component {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setChartData(Map<String, Long> dataMap) {
        try {
            String json = objectMapper.writeValueAsString(dataMap);
            getElement().callJsFunction("setData", Json.parse(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}