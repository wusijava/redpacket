package com.zanclick.redpacket.user.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/7 18:14
 */
@Data
public class UserRole implements Identifiable<Long> {
    private Long id;

    private  String userId;

    private Integer roleType;
}
