package com.risk.riskmanage.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSheetModel {
    private String sheetName;//sheet名
    private List<String> headers;//sheet中第一行内容
    private List<List> data;//sheet数据
}
