package com.risk.riskmanage.common.model.requestParam;

import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class UpdateStatusParam {
    private String ids;
    private Integer status;
    private List<Long> list;

    public static boolean checkParam(UpdateStatusParam param) {
        try {
            String[] split = param.getIds().split(",");
            Integer status = param.getStatus();
            if (split == null || split.length == 0 || status == null) {
                throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(), ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
            }
            param.list = new ArrayList<>();
            for (String s : split) {
                param.list.add(Long.valueOf(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(), ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        return true;
    }
}
