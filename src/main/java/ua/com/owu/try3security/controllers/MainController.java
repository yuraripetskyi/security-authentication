package ua.com.owu.try3security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.try3security.models.Customer;
import ua.com.owu.try3security.services.CustomerService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

  @Autowired
  CustomerService customerService;
  @Autowired
  private JavaMailSender sender;

  @Autowired
  private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Customer customer){

        if(customer.isEnabled()){
        return "user";}else {
            return "login-error";
        }

    }

    @PostMapping("/ok")
    public String success(Model model){

        Customer principal = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.getUsername();

        model.addAttribute("user",principal);

        return "success";
    }
    @GetMapping("/login-error")
    public String error(){
        return "loh";
    }

    @PostMapping("/save")
    public String save(
            Customer customer,
            @RequestParam MultipartFile file

    ) throws MessagingException, IOException {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        String path = System.getProperty("user.dir")+ File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator
                + "static"
                + File.separator;

        file.transferTo(new File(path + file.getOriginalFilename()));
        customer.setImage(path+file.getOriginalFilename());


        System.out.println(customer);
        customerService.save(customer);
        sendMail(customer.getEmail());



        return "login";
    }


    @GetMapping("/activate/{id}")
    public String activate(@PathVariable int id){

        Customer user = (Customer) customerService.loadUserById(id);
        user.setEnabled(true);
        customerService.save(user);
        return "login";
    }

    private void sendMail(String email) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        Customer customer = (Customer) customerService.loadUserByEmail(email);
        int id = customer.getId();
//
        String text = "go to the link, to activate ur account : <a href='http://localhost:8080/activate/"+id+"'>activate</a>";
        System.out.println(text);


        helper.setText(text,true);

        helper.setSubject("huinia");
        helper.setTo(email);

        sender.send(mimeMessage);
    }

@PostMapping("/user/{id}")
    public  String userPage(Model model, @PathVariable int id){

    Customer customer = (Customer) customerService.loadUserById(id);

    model.addAttribute("user", customer);

    return "user";
}


}
