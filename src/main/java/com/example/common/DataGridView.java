package com.example.common;

/**
 * @ProjectName: project-demo
 * @Package: com.example.common
 * @ClassName: DataGridView
 * @Author: 游佳琪
 * @Description: json数据实体
 * @Date: 2020-8-14 15:38
 * @Version: 1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json数据实体
 * @author LJH
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView {

    private Integer code = 0;
    private String msg = "";
//    数据的数目
    private Long count = 0L;
//    数据
    private Object data;

    public DataGridView(Long count, Object data) {
        super();
        this.count = count;
        this.data = data;
    }

    public DataGridView(Object data) {
        super();
        this.data = data;
    }
}