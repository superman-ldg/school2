package com.ldg.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
    @TableId(type = IdType.INPUT)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long id;
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long uid;
    private String title;
    private String context;
    @TableField(fill = FieldFill.INSERT)
    private String createtime;
    @TableLogic
    private Integer deleted;
    private String url;
    private Integer fabulous;
    private Integer type;
}
