package com.zanclick.redpacket.user.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvlu
 * @date 2019-03-06 17:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Serializable {
    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String uid;

    private String password;

    private String salt;

}
