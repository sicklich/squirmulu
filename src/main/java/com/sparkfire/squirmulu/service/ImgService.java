package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.dao.ImgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgService {
    @Autowired
    ImgDao imgDao;
}
