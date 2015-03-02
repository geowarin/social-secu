package hello.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;

@Controller
public class HelloController {

    private Twitter twitter;
    private GitHub github;
    private ConnectionRepository connectionRepository;

    @Inject
    public HelloController(Twitter twitter, GitHub github, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.github = github;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping("/github")
    public String helloGithub(Model model) {
        if (connectionRepository.findPrimaryConnection(GitHub.class) == null) {
            return "redirect:/connect/github";
        }

        model.addAttribute("profile", github.userOperations().getUserProfile());
        return "helloGithub";
    }

    @RequestMapping("/twitter")
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        model.addAttribute(twitter.userOperations().getUserProfile());
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline();
        model.addAttribute("tweets", tweets);
        return "helloTwitter";
    }
}
