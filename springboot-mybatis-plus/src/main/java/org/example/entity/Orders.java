package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long id;

    private Integer userId;

    private String number;

    private Date createtime;

    private String note;

    private User user;

    private List<Orderdetail> detailList;
}
