package BugiSquad.HaksikMatnam.mvc.contoller;


import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.menu.etc.*;
import BugiSquad.HaksikMatnam.menu.service.MenuService;
import BugiSquad.HaksikMatnam.menu.service.NewMenuService;
import BugiSquad.HaksikMatnam.mvc.dto.WaitingOrderDetailDto;
import BugiSquad.HaksikMatnam.order.etc.OrderType;
import BugiSquad.HaksikMatnam.order.service.OrdersService;
import BugiSquad.HaksikMatnam.util.s3.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

import static BugiSquad.HaksikMatnam.util.Access.Token;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MenuService menuService;
    private final S3Service s3Service;
    private final OrdersService orderService;
    private final NewMenuService newMenuService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @GetMapping("/main")
    public String index() {
        return "pages/main";
    }

    @GetMapping("/order")
    public String findOrderAllMvc(Model model) {
        List<WaitingOrderDetailDto> orders = orderService.findOrderNotInCompleteMvc();
        model.addAttribute("orderDetail", orders);
        return "pages/order";
    }


    @ResponseBody
    @GetMapping("/order/refresh")
    public ResponseEntity findOrderAllRefreshMvc() {
        List<WaitingOrderDetailDto> orders = orderService.findOrderNotInCompleteMvc();
        return new ResponseEntity<>(DataResponse.response(200,orders),HttpStatus.OK);
    }

    @GetMapping("/order/change-order-type/{order-type}/{orderId}")
    public String changeOrderTypeToCook(
            @PathVariable(value = "orderId") Long orderId,
            @PathVariable(value = "order-type") OrderType orderType,
            @RequestHeader(Token) String token) {
        orderService.changeOrderTypeMvc(orderId, orderType,jwtTokenProvider.getUserPk(token));
        return "redirect:/order";
    }


    /** 관리자 주문 거절 **/
    @GetMapping("/order/deny/{orderId}")
    public String denyOrderWithAdmin(
            @PathVariable(value = "orderId") Long orderId,
            @RequestHeader(Token) String token
    ) {
        orderService.deleteOrderWithAdminMvc(orderId, jwtTokenProvider.getUserPk(token));
        return "redirect:/order";
    }


    @GetMapping("/order-complete")
    public String orderComplete(Model model) {
        List<WaitingOrderDetailDto> orders = orderService.findOrderCompleteMvc();
        model.addAttribute("orderDetail", orders);
        return "pages/orderComplete";
    }

    @GetMapping("/product-register")
    public String productRegister() {
        return "pages/product-register";
    }


    @GetMapping("/product")
    public String findMenusAll(Model model) {
        List<MenuDto> menuList = menuService.findMenusAllMvc();
        model.addAttribute("menuList", menuList);

        return "pages/products";
    }

    @GetMapping("/new-food/register")
    public String newFoodRegister(Model model) {
        return "pages/newFoodRegister";
    }


    @GetMapping("/new-food")
    public String newFood(Model model) {

        List<NewMenuDto> newMenusMvc = newMenuService.findNewMenusMvc();
        model.addAttribute("newMenus", newMenusMvc);
        return "pages/new-menu";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/contact";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @PostMapping(value = "/menu", consumes = "multipart/form-data")
    public String postMenu(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("detail") String detail,
            @RequestParam("price") int price,
            @RequestParam("category") String category,
            @RequestHeader("accessToken") String token) {

        // 파일 업로드
        String imageUrl = s3Service.upload(file, "png");
        MenuPostDto menuPostDto = new MenuPostDto(name, detail, imageUrl, price, category);
        menuService.postMenu(menuPostDto, jwtTokenProvider.getUserPk(token));
        return "redirect:/product";
    }

    @GetMapping("/menu/new")
    public String showNewFoodForm(Model model) {
        model.addAttribute("newMenuPostDto", new NewMenuPostDto());
        return "newFoodRegister";
    }

    // POST request for handling the new food registration form submission
    @PostMapping("/new-menu/register")
    public String registerNewFood(@RequestParam("file") MultipartFile file,
                                  @RequestParam("name") String name,
                                  @RequestParam("category") String category,
                                  @RequestParam("detail") String detail,
                                  @RequestHeader("accessToken") String token) {
        // 파일 업로드
        String imageUrl = s3Service.upload(file, "png");
        NewMenuPostDto newMenuPostDto = new NewMenuPostDto(name, detail,imageUrl, category);
        newMenuService.postNewMenu(newMenuPostDto, jwtTokenProvider.getUserPk(token));
        return "redirect:/new-food"; // redirect to the new food registration form
    }


    @PostMapping("/menu/update")
    public String updateMenu(
            @ModelAttribute("menu") MenuUpdateDto menuDto,
            @RequestParam("newImage") MultipartFile newImage,
            @RequestHeader("accessToken") String token) {
        // 이미지 첨부 여부에 따라 처리
        if (!newImage.isEmpty()) {
            // 새로운 이미지 업로드
            String url = s3Service.upload(newImage, "png");
            menuDto.setImageUrl(url);
        }

        // 메뉴 업데이트
        menuService.updateMenu(menuDto, jwtTokenProvider.getUserPk(token));
        return "redirect:/product";
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteMenuMvc(
            @PathVariable("id") Long id,
            @RequestHeader("accessToken") String token) {
        menuService.deleteMenu(id, jwtTokenProvider.getUserPk(token));
        return "redirect:/product";
    }

    @GetMapping("/menu/edit/{id}")
    public String showEditMenuForm(@PathVariable("id") Long id, Model model) {
        // 메뉴 정보 조회
        MenuDto menuDto = menuService.findMenuById(id);
        model.addAttribute("menuDto", menuDto);
        return "pages/product-edit";
    }

    @GetMapping("/new-menu/delete/{id}")
    public String deleteNewMenuMvc(@PathVariable("id") Long id) {
        newMenuService.deleteByIdMvc(id);
        return "redirect:/new-food";
    }

    @GetMapping("/new-menu/edit/{id}")
    public String showEditNewMenuForm(@PathVariable("id") Long id, Model model) {
        // 메뉴 정보 조회
        NewMenuDto newMenu = newMenuService.findNewMenuMvc(id);
        model.addAttribute("newMenu", newMenu);
        return "pages/new-food-edit";
    }

    @PostMapping("/new-menu/update")
    public String updateMenu(
            @ModelAttribute("menu") NewMenuUpdateDto newMenuUpdateDto
            , @RequestParam("newImage") MultipartFile newImage,
            @RequestHeader("accessToken") String token
    ) {
        // 이미지 첨부 여부에 따라 처리
        if (!newImage.isEmpty()) {
            // 새로운 이미지 업로드
            String url = s3Service.upload(newImage, "png");
            newMenuUpdateDto.setUrl(url);
        }
        log.info("newMenuDto {}", newMenuUpdateDto);

        // 메뉴 업데이트
        newMenuService.updateNewMenu(newMenuUpdateDto, jwtTokenProvider.getUserPk(token));
        return "redirect:/new-food";
    }

}
