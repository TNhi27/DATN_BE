package com.okteam.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.okteam.dao.CommentRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.DetailsRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.OrderAdDto;
import com.okteam.dto.Orderdto;
import com.okteam.dto.OrdersRequest;
import com.okteam.dto.OrdersResponseDTO;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;

import com.okteam.entity.Response;
import com.okteam.entity.ResponseClient;
import com.okteam.exception.NotEnoughMoney;
import com.okteam.exception.NotFoundSomething;
import com.okteam.utils.DtoUtils;
import com.okteam.entity.Ctv;
import com.okteam.entity.Details;
import com.okteam.entity.Ncc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderRepository oRepository;
    @Autowired
    ProductRepository pRepository;
    @Autowired
    CtvRepository cRepository;
    @Autowired
    NccRepository nRepository;
    @Autowired
    DetailsRepository detaildao;
    @Autowired
    FollowSellRepository fdao;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    DtoUtils dtoUtils;

    // get
    @GetMapping("/get/{idorder}")
    public ResponseEntity<Orders> getNcc(@PathVariable("idorder") Integer idorder) {
        Orders o = oRepository.findById(idorder).orElseThrow(() -> new NotFoundSomething(":( Khong tim thay don hag"));

        return new ResponseEntity<Orders>(o, HttpStatus.OK);
    }

    @GetMapping("/ctv/{ctv}")
    public ResponseEntity<Page<Orders>> selectOrdersWithCtv(@PathVariable("ctv") String ctv,
            @RequestParam Optional<String> status, @RequestParam Optional<Integer> id) {
        Sort sort = Sort.by("dateorder").descending();

        if (id.orElse(-1) <= 0) {
            Page<Orders> page = oRepository.getOrdersWithCtvStatus(ctv, status.orElse("%%"),
                    PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        } else {
            Page<Orders> page = oRepository.getOrdersWithCtvId(ctv, id.orElse(0), PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        }

    }

    @GetMapping("/ncc/{ncc}")
    public ResponseEntity<Page<Orders>> selectOrdersWithNcc(@PathVariable("ncc") String ncc,
            @RequestParam Optional<String> status, @RequestParam Optional<Integer> id) {
        Sort sort = Sort.by("dateorder").descending();

        if (id.orElse(-1) <= 0) {
            Page<Orders> page = oRepository.getOrdersWithNccStatus(ncc, status.orElse("%%"),
                    PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        } else {
            Page<Orders> page = oRepository.getOrdersWithNccId(ncc, id.orElse(0), PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        }

    }

    // post
    @PostMapping
    public ResponseEntity saveOrder(@RequestBody OrdersRequest orderdto) {

        Orders order = new Orders();

        order.setDateorder(new Date());

        order.setStatus(0);
        order.setAddress(orderdto.getAddress());
        order.setCustomer(orderdto.getCustomer());
        order.setSdtcustomer(orderdto.getSdtcustomer());
        order.setPayment(orderdto.getPayment());
        order.setTotal(orderdto.getTotal());
        order.setHuyen(orderdto.getHuyen());
        order.setXa(orderdto.getXa());
        order.setTinh(orderdto.getTinh());
        order.setNote(orderdto.getNote());
        // lay ctv hien tai
        var username_ctv = SecurityContextHolder.getContext().getAuthentication().getName();
        Ctv ctv = cRepository.findById(username_ctv).get();

        if (ctv.getMoney() < orderdto.getTotal()) {
            return new ResponseEntity<>(new ResponseClient<Orders>(order, "Khong du tien", 400), HttpStatus.OK);
        } else {
            ctv.setMoney(ctv.getMoney() - orderdto.getTotal());
            cRepository.save(ctv);
        }
        order.setCtv(ctv);
        try {
            Ncc ncc = nRepository.findById(orderdto.getIdncc(),true);
            order.setNcc(ncc);
        } catch (Exception e) {
           throw new NotFoundSomething(":( Khong tim thay nha cung cap");
        }
       
              

        Orders rsOrder = oRepository.save(order);

        List<Details> details = new ArrayList<>();

        for (int i = 0; i < orderdto.getDetails().size(); i++) {
            Products products = pRepository.findById(orderdto.getDetails().get(i).getIdpro()).get();
            Details d = new Details();
            d.setQty(orderdto.getDetails().get(i).getSl());
            d.setOrders(rsOrder);
            d.setProducts(products);
            d.setRevenue(orderdto.getDetails().get(i).getPrice());
            details.add(d);
            detaildao.save(d);
        }

        return new ResponseEntity<>(
                new ResponseClient<Orders>(oRepository.findById(rsOrder.getIdorder()).get(), "Tao thanh cong", 200),
                HttpStatus.OK);

    }

    @PostMapping("/update_ghn_code")
    public ResponseEntity<Orders> update_ghn(@RequestParam Optional<Integer> id, @RequestParam String code,
            @RequestParam Integer total_fee) {

        String idncc = SecurityContextHolder.getContext().getAuthentication().getName();
        Orders o = oRepository.findById(id.get()).orElseThrow(() -> new NotFoundSomething("Khong tim thay don hang"));

        Ncc ncc = nRepository.findById(idncc, true);

        if (total_fee > ncc.getMoney()) {
            throw new NotEnoughMoney();
        }
        o.setOrder_code(code);
        o.setStatus(2);
        o.setTotal_fee(total_fee);
        oRepository.save(o);

        ncc.setMoney(ncc.getMoney() - total_fee);
        nRepository.save(ncc);

        o.getDetails().stream().map((e) -> {
            Products p = e.getProducts();
            p.setQty(p.getQty() - e.getQty());
            pRepository.save(p);
            return -1;
        });
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Object> setStatus(@PathVariable("id") int id) {
        String idctv = SecurityContextHolder.getContext().getAuthentication().getName();
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething("Đơn không tồn tại"));

        Ctv ctv = cRepository.findById(idctv).get();

        if (o.getStatus() == 0) {
            ctv.setMoney(ctv.getMoney() + o.getTotal());
            cRepository.save(ctv);
            for (var d : o.getDetails()) {
                detaildao.delete(d);
            }
           
            for (var cmt : commentRepository.getCommentOfOrder(o.getIdorder())) {
                commentRepository.delete(cmt);
            }
            oRepository.deleteById(id);
        } else {
            return new ResponseEntity<Object>("Khong the xoa", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>("Xoa thanh cong", HttpStatus.OK);
    }

    @PostMapping("/cancelncc/{id}")
    public ResponseEntity<Object> setStatusncc(@PathVariable("id") int id,@RequestParam Optional<String> lydo ) {
        String ncc = SecurityContextHolder.getContext().getAuthentication().getName();
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething("Đơn không tồn tại"));

        Ctv ctv = o.getCtv();

        if (o.getStatus() == 0) {
            o.setStatus(4);
            o.setLydo(lydo.orElse("Không xác định !"));
            ctv.setMoney(ctv.getMoney() + o.getTotal());
            cRepository.save(ctv);
        } else {
            return new ResponseEntity<Object>(oRepository.save(o), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(oRepository.save(o), HttpStatus.OK);
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable("id") Integer id, @RequestBody Orderdto orderdto) {
        Orders orders = oRepository.findById(id).get();
        if (!nRepository.existsById(orderdto.getIdncc())) {
            System.out.print("Không tìm thấy NCC");
            return new ResponseEntity<Orders>(orders, HttpStatus.NOT_FOUND);
        } else {

            orders.setStatus(orderdto.getStatus());
            orders.setAddress(orderdto.getAddress());
            orders.setCustomer(orderdto.getCustomer());
            orders.setSdtcustomer(orderdto.getSdtcustomer());
            orders.setPayment(orderdto.getPayment());

            // orders.setCtv(cRepository.findById(orderdto.getIdctv()).get());
            orders.setNcc(nRepository.findById(orderdto.getIdncc()).get());
        }

        return new ResponseEntity<Orders>(oRepository.save(orders), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public Response<String> deleteOrders(@PathVariable("id") Integer id) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":(Khong tim thay don hang"));

        if (o.getStatus() == 0) {
            oRepository.deleteById(id);
            return new Response<>(null, null, "Xoa thanh cong");
        }

        return new Response<>(null, null, "Khong the xoa");

    }

    @PostMapping("/update_status")
    public ResponseEntity<Orders> updatestatus(@RequestParam int id, @RequestParam int status) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":( Khong timthay don hang"));
        if (status != 5) {

            o.setStatus(status);
            // giao thanh cong
            if (o.getStatus() == 1) {
                o.getDetails().stream().map((e) -> {
                    Products p = e.getProducts();
                    Integer sl = p.getQty()-e.getQty();
                    if (sl<0) {
                        throw new NotFoundSomething("Số lượng sản phẩm trong kho không đủ để giao: "+p.getIdpro());
                    }
                    p.setQty(sl);
                    pRepository.save(p);
                    return -1;
                });
            }
        }

        return new ResponseEntity<Orders>(oRepository.save(o), HttpStatus.OK);

    }

    @PostMapping("/pay_to_ctv")
    public ResponseEntity<Orders> payctv(@RequestParam int id) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":( Khong timthay don hang"));
        Ncc ncc = o.getNcc();
        Ctv ctv = o.getCtv();

        ctv.setMoney(ctv.getMoney() + o.getPayment());

        if (o.getStatus() == 1) {
            ncc.setMoney(ncc.getMoney() + o.getTotal());
        }

        nRepository.save(ncc);
        cRepository.save(ctv);

        o.setDatefinish(new Date());
        o.setStatus(5);
        return new ResponseEntity<Orders>(oRepository.save(o), HttpStatus.OK);

    }

    @GetMapping("/list")
    public Response<OrdersResponseDTO> listOrders() {
        return new Response<OrdersResponseDTO>(
                dtoUtils.mapOrdersToDto(oRepository.findAll(Sort.by(Sort.Direction.DESC, "dateorder"))), null, "OK");
    }

    @PostMapping("/add")
    public Response<OrdersResponseDTO> assOrders(@RequestBody OrderAdDto ord) {
        Ncc ncc = nRepository.findById(ord.getNcc()).get();
        Ctv ctv = cRepository.findById(ord.getCtv()).get();
        Orders order = new Orders().dtoReturnEntity(ord);
        order.setCtv(ctv);
        order.setNcc(ncc);
        if (order.getStatus() == 5) {
            order.setDatefinish(new Date());
        }
        oRepository.save(order);
        List<Details> details = ord.getDetails().stream().map(o -> {
            Details dt = new Details();
            dt.setQty(o.getSl());
            dt.setRevenue(o.getPrice_customer());
            dt.setProducts(pRepository.findById(o.getSp()).get());
            dt.setOrders(order);
            return dt;
        }).collect(Collectors.toList());
        order.setDetails(detaildao.saveAll(details));
        oRepository.save(order);
        return new Response<OrdersResponseDTO>(
                dtoUtils.mapOrdersToDto(oRepository.findAll(Sort.by(Sort.Direction.DESC, "dateorder"))), null, "OK");
    }

    @DeleteMapping("/delete")
    public Response<OrdersResponseDTO> deleteOrders(@RequestBody OrderAdDto ord) {
        String message = "OK";
        if (!oRepository.existsById(ord.getIdorder())) {
            message = "Không tìm thấy đơn hàng!";
        } else {
            if (ord.getStatus() == 0 || ord.getStatus() == 3 || ord.getStatus() == 4) {
                Orders order = oRepository.findById(ord.getIdorder()).get();
                detaildao.deleteAll(detaildao.findByOrdersEquals(order));
                oRepository.deleteById(ord.getIdorder());
            } else {
                message = "Đơn hàng không ở trạng thái có thể hủy!";
            }
        }
        return new Response<OrdersResponseDTO>(
                dtoUtils.mapOrdersToDto(oRepository.findAll(Sort.by(Sort.Direction.DESC, "dateorder"))), null, message);
    }

    @PutMapping("/update-chitiet")
    public Response<OrdersResponseDTO> update_chitiet(@RequestBody OrderAdDto ord) {
        String message = "OK";
        if (!oRepository.existsById(ord.getIdorder())) {
            message = "Không tìm thấy đơn hàng!";
        } else {
            Orders od = oRepository.findById(ord.getIdorder()).get();
            if (od.getStatus() == 0 || od.getStatus() == 3 || od.getStatus() == 4) {
                Orders order = new Orders().dtoReturnEntity(ord);
                Ncc ncc = nRepository.findById(ord.getNcc()).get();
                Ctv ctv = cRepository.findById(ord.getCtv()).get();
                detaildao.deleteAll(detaildao.findByOrdersEquals(order));
                order.setCtv(ctv);
                order.setNcc(ncc);
                order.setDateorder(od.getDateorder());
                if (ord.getStatus() == 5) {
                    order.setDatefinish(new Date());
                } else {
                    order.setDatefinish(null);
                }
                List<Details> details = ord.getDetails().stream().map(o -> {
                    Details dt = new Details();
                    dt.setQty(o.getSl());
                    dt.setRevenue(o.getPrice_customer());
                    dt.setProducts(pRepository.findById(o.getSp()).get());
                    dt.setOrders(order);
                    return dt;
                }).collect(Collectors.toList());
                order.setDetails(detaildao.saveAll(details));
                oRepository.save(order);
            } else {
                message = "Trạng thái đơn hàng không phù hợp để cập nhật!";
            }
        }
        return new Response<OrdersResponseDTO>(
                dtoUtils.mapOrdersToDto(oRepository.findAll(Sort.by(Sort.Direction.DESC, "dateorder"))), null, message);
    }

    @PutMapping("/reform/{id}")
    public Response<OrdersResponseDTO> reform(@PathVariable("id") Integer id, @RequestParam("thaotac") Integer thaotac,
            @RequestParam("value") String value) {
        String datefinish = null;
        if (!oRepository.existsById(id)) {
            return new Response<OrdersResponseDTO>(null, null, "Không tìm thấy đơn hàng!");
        }
        Orders ord = oRepository.findById(id).get();
        Integer arr[] = { 1, 5 };
        if (Arrays.asList(arr).contains(thaotac) && value.isEmpty()) {
            return new Response<OrdersResponseDTO>(null, ord, "Giá trị không hợp lệ");
        }
        if (thaotac != 0 && ord.getStatus() != 0 && ord.getStatus() != 3 && ord.getStatus() != 4) {
            return new Response<OrdersResponseDTO>(null, ord, "Trạng thái đơn hàng không phù hợp để cập nhật!");
        }
        switch (thaotac) {
            case 0:
                int status = Integer.parseInt(value);
                ord.setStatus(status);
                Date date = new Date();
                if (status == 5) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    datefinish = formatter.format(date);
                } else {
                    date = null;
                }
                ord.setDatefinish(date);
                break;
            case 1:
                ord.setCustomer(value);
                break;
            case 2:
                ord.setSdtcustomer(value);
                break;
            case 5:
                ord.setAddress(value);
                break;
            case 6:
                ord.setOrder_code(value);
                break;
            default:
                return new Response<OrdersResponseDTO>(null, null, "Thao tác không hợp lệ!");
        }
        oRepository.save(ord);
        return new Response<OrdersResponseDTO>(null, datefinish, "OK");
    }

    @GetMapping("/getone")
    public Response<OrdersResponseDTO> getOne(@RequestParam("idorder") Integer idorder) {
        if (!oRepository.existsById(idorder)) {
            return new Response<OrdersResponseDTO>(null, null, "Không tìm thấy đơn hàng");
        }
        Orders ord = oRepository.findById(idorder).get();
        return new Response<OrdersResponseDTO>(
                dtoUtils.mapOrdersToDto(oRepository.findAll(Sort.by(Sort.Direction.DESC, "dateorder"))),
                new OrdersResponseDTO().createByEntity(ord), "OK");
    }

}