package com.zanclick.redpacket.user.query;

import com.zanclick.redpacket.user.entity.Role;
import lombok.Data;

@Data
public class RoleQuery extends Role {
    private Integer page;

    private Integer limit;

    private Integer offset;

  //  private String sort;
}
