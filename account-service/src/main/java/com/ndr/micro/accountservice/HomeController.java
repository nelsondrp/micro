package com.ndr.micro.accountservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    @GetMapping("/hello")
    public ResponseEntity<?> hello()
    {
        return ResponseEntity.ok().body("hello");
    }

    @PostMapping("/post")
    public ResponseEntity<?> helpost(@RequestBody String post)
    {
        return ResponseEntity.ok().body(post);
    }
}
