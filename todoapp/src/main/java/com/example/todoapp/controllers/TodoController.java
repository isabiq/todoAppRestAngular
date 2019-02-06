package com.example.todoapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {
    
    @Autowired
    TodoRepository todoRepository;
    
    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return todoRepository.findAll(sortByCreatedAtDesc);
    }
    
    @PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo) {
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }
    
    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") String id) {
        Todo todo = todoRepository.findOne(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        }
    }
    
    @PutMapping(value = "/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") String id, @Valid @RequestBody Todo todo) {
        Todo todoData = todoRepository.findOne(id);
        if (todoData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        todoData.setTitle(todo.getTitle());
        todoData.setCompleted(todo.getCompleted());
        Todo updatedTodo = todoRepository.save(todoData);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/todos/{id}")
    public void deleteTodo(@PathVariable("id") String id) {
        todoRepository.delete(id);
    }
    
    // @Configuration
    // @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    // public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //
    // public static final String LOGIN = "/login";
    //
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http.exceptionHandling().authenticationEntryPoint(new
    // AjaxAwareEntryPoint(LOGIN))
    // .and()
    // .authorizeRequests()
    // .antMatchers("/api/**").authenticated()
    // .anyRequest().permitAll()
    // .and()
    // .formLogin().loginPage(LOGIN)
    // .and()
    // .csrf().csrfTokenRepository(csrfTokenRepository())
    // .and()
    // .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    // }
    //
    // private CsrfTokenRepository csrfTokenRepository() {
    // HttpSessionCsrfTokenRepository repository = new
    // HttpSessionCsrfTokenRepository();
    // repository.setHeaderName("X-XSRF-TOKEN");
    // return repository;
    // }
    //
    // @Override
    // public void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // auth.inMemoryAuthentication()
    // .withUser("admin").password("admin").roles("ADMIN", "USER")
    // .and()
    // .withUser("user").password("user") .roles("USER");
    // }
    // }
    //
    // class AjaxAwareEntryPoint extends LoginUrlAuthenticationEntryPoint {
    //
    // private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    // private static final String X_REQUESTED_WITH = "X-Requested-With";
    //
    // public AjaxAwareEntryPoint(String loginFormUrl) {
    // super(loginFormUrl);
    // }
    //
    // @Override
    // public void commence(HttpServletRequest request, HttpServletResponse
    // response, AuthenticationException exception)
    // throws IOException, ServletException {
    // if (XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH))) {
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    // } else {
    // super.commence(request, response, exception);
    // }
    // }
    // }
    //
    // class CsrfHeaderFilter extends OncePerRequestFilter {
    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response, FilterChain filterChain)
    // throws ServletException, IOException {
    // CsrfToken csrf = (CsrfToken)
    // request.getAttribute(CsrfToken.class.getName());
    // if (csrf != null) {
    // Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
    // String token = csrf.getToken();
    // if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
    // cookie = new Cookie("XSRF-TOKEN", token);
    // cookie.setPath("/");
    // response.addCookie(cookie);
    // }
    // }
    // filterChain.doFilter(request, response);
    // }
    // }
    
}
