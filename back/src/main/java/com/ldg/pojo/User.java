package com.ldg.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ldg.pojo.base.BaseUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class User extends BaseUser implements Serializable {
    @TableId
    private Long id;
    private Long id_card;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String url;
    private int status;
    /**
     * status:用户身份
     */

}
