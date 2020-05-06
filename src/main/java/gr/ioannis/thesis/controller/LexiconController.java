package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/lexicon/")
public class LexiconController {

  private KeyService keyService;

  @Autowired
  public LexiconController(KeyService keyService) {
    this.keyService = keyService;
  }

  @GetMapping()
  public Map<String, String> getTranslations(@RequestParam String lang) {
    return keyService.getTranslationsForGroupNameAndLocale("ui", lang);
  }


}
