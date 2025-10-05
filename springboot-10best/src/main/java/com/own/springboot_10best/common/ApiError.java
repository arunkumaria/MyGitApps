package com.own.springboot_10best.common;

import java.time.Instant;

public record ApiError(Instant timestamp,int status,String error,String path){}