package com.zanclick.redpacket.user.query;

import com.zanclick.redpacket.user.entity.User;
import lombok.Data;

@Data
public class UserQuery extends User {
    private Integer page;

    private Integer limit;

    private Integer offset;

    private String startTime;

    private String endTime;
}
