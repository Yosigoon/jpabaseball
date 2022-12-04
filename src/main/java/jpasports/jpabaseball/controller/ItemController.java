package jpasports.jpabaseball.controller;

import jpasports.jpabaseball.domain.item.Glove;
import jpasports.jpabaseball.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new GloveForm());
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(GloveForm form) {
        Glove glove = new Glove();
        glove.setName(form.getName());  //실무에선 setter 제거하고 생성자에 세팅하자
        glove.setPrice(form.getPrice());
        glove.setStockQuantity(form.getStockQuantity());
        glove.setBrand(form.getBrand());
        glove.setPosition(form.getPosition());

        itemService.saveItem(glove);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "/items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Glove item = (Glove) itemService.findOne(itemId);

        GloveForm form = new GloveForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setBrand(item.getBrand());
        form.setPosition(item.getPosition());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") String itemId, @ModelAttribute("form") GloveForm form) {
        Glove glove = new Glove();
        glove.setId(form.getId());
        glove.setName(form.getName());
        glove.setPrice(form.getPrice());
        glove.setStockQuantity(form.getStockQuantity());
        glove.setBrand(form.getBrand());
        glove.setPosition(form.getPosition());

        itemService.saveItem(glove);
        return "redirect:/items";
    }
}
