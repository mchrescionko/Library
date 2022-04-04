package com.example.library.controller;
import com.example.library.request.SearchRequest;
import com.example.library.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchBookController {
    private SearchService searchService;

    public SearchBookController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    String search(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "search";
    }

    @PostMapping("/search")
    String searchPost(@ModelAttribute("searchRequest") SearchRequest searchRequest, Model model) {
        model.addAttribute("books", searchService.search(searchRequest));
        return "searchResult";
    }

    @PostMapping("/searchBook")
    String searchBook(Model model, String title){
       SearchRequest searchRequest = SearchRequest.builder()
               .title(title).build();
       model.addAttribute("books", searchService.search(searchRequest));
       return "searchResult";
    }
}
