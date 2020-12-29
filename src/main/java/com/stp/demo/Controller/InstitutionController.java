package com.stp.demo.Controller;

import com.stp.demo.model.Card;
import com.stp.demo.model.Institution;
import com.stp.demo.model.User;
import com.stp.demo.postRequest.Data;
import com.stp.demo.postRequest.DataInstUpd;
import com.stp.demo.service.CardService;
import com.stp.demo.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class InstitutionController {

    private final InstitutionService institutionService;
    private final CardService cardService;

    @Autowired
    public InstitutionController(InstitutionService institutionService, CardService cardService) {
        this.institutionService = institutionService;
        this.cardService = cardService;
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @RequestMapping(value = "/create-inst/confirmed", method = RequestMethod.POST)
    public void updateCard(@RequestBody Data data) {
        Card card = cardService.findCardById(data.getId());

        if (!card.findInListByInstName(card, data.getName())) {

            Institution inst = institutionService.selectInstByName(data.getName());
            if (inst != null) {  /*уже существует*/
                card.addNewInst(inst);
            } else {
                Institution newInst = new Institution();
                newInst.setName(data.getName());
                card.addNewInst(newInst);
            }

            cardService.saveCard(card);
        }
    }


    @CrossOrigin(origins = "http://localhost:4000")
    @GetMapping("/inst-update/{id_card}/{id_inst}/getInfo")
    public Institution getInstForm(@PathVariable("id_card") Long id_card, @PathVariable("id_inst") Long id_inst) {
        Institution institution = institutionService.selectInstByName(id_inst);
        return institution;
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @RequestMapping(value = "/update-inst/confirmed", method = RequestMethod.POST)
    public void updateInst(@RequestBody DataInstUpd data) {
        Card card = cardService.findCardById(data.getId());

        if (!card.findInListByInstName(card, data.getName())) {

            /* Удаляем из коллекции старое имя */
            for (Institution institution : card.getInstitutions()) {
                if (institution.getName().equals(data.getOldName())) {
                    card.getInstitutions().remove(institution);
                    break;
                }
            }

            /* Новое имя уже существует? */
            Institution inst = institutionService.selectInstByName(data.getName());
            if (inst != null) {  /*уже существует*/
                card.addNewInst(inst);
            } else { /* имя не существует. Создадим новый объект */
                Institution newInst = new Institution();
                newInst.setName(data.getName());
                card.addNewInst(newInst);
            }
            cardService.saveCard(card);

            inst = institutionService.selectInstByName(data.getOldName());
            if (inst.getCards().size() == 0) {
                institutionService.deleteById(inst.getId());
            }

        }
    }

    @CrossOrigin(origins = "http://localhost:4000")
    @RequestMapping(value = "/inst-del/confirmed", method = RequestMethod.POST)
    public void deleteInst(@RequestBody Data data) {
        Card card = cardService.findCardById(data.getId());

        /* Удаляем из коллекции старок имя */
        for (Institution institution : card.getInstitutions()) {
            if (institution.getName().equals(data.getName())) {
                card.getInstitutions().remove(institution);
                break;
            }
        }
        cardService.saveCard(card);

        Institution inst = institutionService.selectInstByName(data.getName());
        if (inst.getCards().size() == 0) {
            institutionService.deleteById(inst.getId());
        }
    }

}
