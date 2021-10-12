package com.okteam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundSomething extends RuntimeException {
    String message;
}