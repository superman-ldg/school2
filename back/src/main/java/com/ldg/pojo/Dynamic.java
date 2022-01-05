package com.ldg.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.base.BaseDynamic;
import com.ldg.pojo.enums.DynamicType;
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
public class Dynamic extends BaseDynamic implements Serializable {
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
    private int fabulous;
    private int type;
}
