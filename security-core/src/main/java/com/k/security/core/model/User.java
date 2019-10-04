package com.k.security.core.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    /**
     * 定义几个JsonView，根据不同场景或者不同的权限返回同的Json视图
     * UserSampleView:简单视图，UserDetailView详细视图，包含简单视图的所有字段
     */
    public interface UserSampleView {
    }

    public interface UserDetailView extends UserSampleView {
    }

    @JsonView(value = UserDetailView.class)
    private int id;

    @JsonView(value = UserSampleView.class)
    private String userName;

    private String password;
}
