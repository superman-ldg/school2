package com.ldg.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ldg.pojo.base.BaseUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
//import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class User extends BaseUser implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long id;
    @TableField(value = "idCard")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long idCard;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String url;
    private Integer status;
    /**
     * status:用户身份
     */

}
