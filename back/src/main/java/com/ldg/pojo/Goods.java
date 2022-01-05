package com.ldg.pojo;


import com.baomidou.mybatisplus.annotation.*;
import com.ldg.pojo.base.BaseGoods;
import com.ldg.pojo.enums.GoodsType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseGoods implements Serializable {
    @TableId
    private Long id;
    private Long uid;
    private String title;
    private String context;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    @TableLogic
    private int deleted;
    private String url;
    private int type;
}
