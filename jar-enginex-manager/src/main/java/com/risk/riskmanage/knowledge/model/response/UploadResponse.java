package com.risk.riskmanage.knowledge.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
        private Integer existRows;//已存在行数
        private Integer sucRows;//成功条数
        private Integer repeatRows;//重复条数
        private Integer failRows;//失败条数
        private Integer total;
        private Map result;//执行结果
}
