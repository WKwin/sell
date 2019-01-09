package com.kwin.sell.common.exception;

import com.kwin.sell.common.enums.ResultEnum;

import lombok.Data;

/**
 * 异常处理类
 * @author Kwin
 *
 */
@Data
public class SellException extends RuntimeException {
	private Integer code;

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

}
