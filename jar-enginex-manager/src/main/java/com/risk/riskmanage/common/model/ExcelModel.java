package com.risk.riskmanage.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelModel {
    private String name;//excel文件名
    private String type;//类型：xlsx，xls
    private List<ExcelSheetModel> sheets;//工作簿
}
