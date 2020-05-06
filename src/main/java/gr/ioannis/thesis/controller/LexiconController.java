package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/lexicon")
public class LexiconController {

  private KeyService keyService;

  private LanguageService languageService;

  @Autowired
  public LexiconController(KeyService keyService, LanguageService languageService) {
    this.keyService = keyService;
    this.languageService = languageService;
  }

  @GetMapping()
  public Map<String, String> getLexicon(@RequestParam String lang) {
    return keyService.getTranslationsForGroupNameAndLocale("ui", lang);
  }

  @GetMapping(value = "/languages")
  public List<LanguageDTO> getLanguages() {
    return languageService.getLanguages(false);
  }


}
