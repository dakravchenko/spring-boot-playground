package net.hackyourfuture.WeekFourRestAPI.category.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {
  private Long id;
  private String name;
  private String image;
}