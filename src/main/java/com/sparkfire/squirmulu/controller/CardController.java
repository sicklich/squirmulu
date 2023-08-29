package com.sparkfire.squirmulu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.PlayerCard;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.service.CardService;
import com.sparkfire.squirmulu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/squ/game")
@CrossOrigin(origins = "*")
public class CardController {
    @Autowired
    CardService cardService;

    @RequestMapping("/card-npc/new-card")
    public CommonResponse newNpcCard(@RequestBody NpcCard card) {
        return CommonResponse.success(cardService.createNpcCard(card));
    }

    @RequestMapping("/card-npc/delete-card")
    public CommonResponse delete_npc_card(@RequestParam(value = "id", required = true) long id) {
        return CommonResponse.success(cardService.deleteNpcCard(id));
    }

    @RequestMapping("/card-npc/update-card")
    public CommonResponse update_npc_card(@RequestBody IndexBody indexBody) throws JsonProcessingException {
        return CommonResponse.success(cardService.updateNpcCard(indexBody));
    }

    @RequestMapping("/card-player/new-card")
    public CommonResponse new_player_card(@RequestBody PlayerCard playerCard) {
        return CommonResponse.success(cardService.createPlayerCard(playerCard));
    }

    @RequestMapping("/card-player/delete-card")
    public CommonResponse delete_player_card(@RequestParam(value = "id", required = true) long id) {
        return CommonResponse.success(cardService.deletePlayerCard(id));
    }

    @RequestMapping("/card-player/update-card")
    public CommonResponse update_player_card(@RequestBody IndexBody indexBody) throws JsonProcessingException {
        return CommonResponse.success(cardService.updatePlayerCard(indexBody));
    }

}
