package com.sparkfire.squirmulu.controller;

import com.sparkfire.squirmulu.dao.TrackClickDao;
import com.sparkfire.squirmulu.dao.TrackStayDao;
import com.sparkfire.squirmulu.dao.TrackViewDao;
import com.sparkfire.squirmulu.entity.TrackClick;
import com.sparkfire.squirmulu.entity.TrackStay;
import com.sparkfire.squirmulu.entity.TrackView;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/squ/track/general")
public class TrackController {

    @Autowired
    private TrackViewDao trackViewDao;

    @Autowired
    private TrackClickDao trackClickDao;

    @Autowired
    private TrackStayDao trackStayDao;

    @RequestMapping("/page-view")
    public CommonResponse insertTrackView(@RequestBody TrackView trackView) {
        int result = trackViewDao.insert(trackView);
        if (result > 0) {
            return CommonResponse.success("success");
        } else {
            return CommonResponse.error(-1, "上报失败");
        }
    }

    @RequestMapping("/click-event")
    public CommonResponse insertTrackClick(@RequestBody TrackClick trackClick) {
        int result = trackClickDao.insert(trackClick);
        if (result > 0) {
            return CommonResponse.success("success");
        } else {
            return CommonResponse.error(-1, "上报失败");
        }
    }

    @RequestMapping("/page-stay")
    public CommonResponse insertTrackStay(@RequestBody TrackStay trackStay) {
        int result = trackStayDao.insert(trackStay);
        if (result > 0) {
            return CommonResponse.success("success");
        } else {
            return CommonResponse.error(-1, "上报失败");
        }
    }
}
