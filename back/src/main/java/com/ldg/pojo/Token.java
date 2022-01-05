package com.ldg.pojo;

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
public class Token implements Serializable {
}
