package com.stp.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stp.demo.model.Card;
import com.stp.demo.model.Institution;
import com.stp.demo.model.User;
import com.stp.demo.postRequest.Data;
import com.stp.demo.service.CardService;
import com.stp.demo.service.InstitutionService;
import com.stp.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
public class CardController {

    private final CardService cardService;
    private final UserService userService;
    private final InstitutionService institutionService;

    @Autowired
    public CardController(CardService cardService, ObjectMapper mapper, UserService userService, InstitutionService institutionService) {
        this.cardService = cardService;
        this.userService = userService;
        this.institutionService = institutionService;
    }

    @RequestMapping(value="/new-card/confirmed", method = RequestMethod.POST)
    public void createNewCard(@RequestBody Data data){
        User user = userService.getUserInfo(data.getId());
        Card card = new Card();
        card.setHeadline(data.getName());
        card.setUser(user);
        cardService.saveCard(card);
    }

    @GetMapping("/card-update/{id_card}")
    public Card updateCardForm(@PathVariable("id_card") Long id){
        Card card = cardService.findCardById(id);
        return card;
    }

    @RequestMapping(value="/update-card/confirmed", method = RequestMethod.POST)
    public void updateCard(@RequestBody Data data){
        cardService.updCard(data.getName(), data.getId());
    }

    @GetMapping(value="/del-card/{idCard}")
    public String DelCard(@PathVariable("idCard") Long id){
        Card card = cardService.findCardById(id);
        List<String> instNameInCard = new ArrayList<String>();

        for(Institution institution: card.getInstitutions()){
            instNameInCard.add(institution.getName());
        }

        card.cleanInstList();
        cardService.saveCard(card);
        cardService.deleteById(id);

        /* Выполним очистку из таблици Институты */
        for (String oldInstName: instNameInCard){
            Institution inst = institutionService.selectInstByName(oldInstName);
            if(inst.getCards().size() == 0){
                institutionService.deleteById(inst.getId());
            }
        }

        return "redirect:user";
    }


}
