package com.stockapp.stocktakingmanagementservice.core.services;

import com.stockapp.stocktakingmanagementservice.core.dtos.SaleProductDto;
import com.stockapp.stocktakingmanagementservice.core.dtos.request.SaleDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.SaleDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Sale;
import com.stockapp.stocktakingmanagementservice.core.port.repositories.SaleRepository;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetCustomerByNameUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.GetProductByNameUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.VerifyProductsByNameUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.sale.CreateSaleUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.sale.GetAllSalesUseCase;
import com.stockapp.stocktakingmanagementservice.utils.enums.SaleType;
import com.stockapp.stocktakingmanagementservice.utils.mappers.SaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Service
public class SaleService implements CreateSaleUseCase, GetAllSalesUseCase {

    private final GetCustomerByNameUseCase getCustomerByNameUseCase;
    private final GetProductByNameUseCase getProductByNameUseCase;
    private final VerifyProductsByNameUseCase verifyProductsByNameUseCase;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Autowired
    public SaleService(GetCustomerByNameUseCase getCustomerByNameUseCase, GetProductByNameUseCase getProductByNameUseCase, VerifyProductsByNameUseCase verifyProductsByNameUseCase, SaleRepository saleRepository, SaleMapper saleMapper) {
        this.getCustomerByNameUseCase = getCustomerByNameUseCase;
        this.getProductByNameUseCase = getProductByNameUseCase;
        this.verifyProductsByNameUseCase = verifyProductsByNameUseCase;
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public Mono<SaleDtoRes> create(SaleDtoReq saleDtoReq) {
        return getCustomerByNameUseCase.getByName(saleDtoReq.getCustomerName())
                .flatMap(customer -> verifyProductsByNameUseCase.validateProducts(saleDtoReq.getProducts())
                        .collectList()
                        .flatMap(products -> {

                            BigDecimal totalCost = BigDecimal.ZERO; // Inicializar el totalCost a 0

                            for (SaleProductDto saleProduct : products) {
                                BigDecimal productCost = BigDecimal.ZERO;
                                if (saleDtoReq.getSaleType().equals("WHOSALE") && saleProduct.getQuantity() >= 12) {
                                    productCost = saleProduct.getCost().multiply(BigDecimal.valueOf(saleProduct.getQuantity()));
                                } else if (saleDtoReq.getSaleType().equals("WHOSALE") && saleProduct.getQuantity() < 12) {
                                    return Mono.error(new RuntimeException("Cantida no permitida para vender al mauor"));
                                } else {
                                    productCost = saleProduct.getCost().multiply(BigDecimal.valueOf(saleProduct.getQuantity()));
                                }
                                totalCost = totalCost.add(productCost);
                            }

                            Sale sale = new Sale.Builder()
                                    .setCustomer(customer)
                                    .setProducts(Arrays.asList(saleDtoReq.getProducts().toArray()))
                                    .setAmount(totalCost)
                                    .build();

                            if (saleDtoReq.getSaleType().equals("RETAIL")) {
                                sale.setSaleType(SaleType.RETAIL);
                            } else if (saleDtoReq.getSaleType().equals("WHOSALE")) {
                                sale.setSaleType(SaleType.WHOSALE);
                                BigDecimal discount = totalCost.multiply(BigDecimal.valueOf(0.2)); // 0.2 representa el 20%
                                sale.setAmount(sale.getAmount().subtract(discount));
                            } else {
                                return Mono.error(new RuntimeException("El tipo de venta no es correcto."));
                            }

                            sale.getAmount().setScale(2, RoundingMode.UP);

                            return saleRepository.save(sale).map(saleMapper::entityToDtoRes);
                        }));

    }

    @Override
    public Flux<SaleDtoRes> getAll() {
        return saleRepository.findAll()
                .collectList()
                .flatMapMany(sales -> {
                    if (sales.isEmpty()) {
                        return Mono.error(new RuntimeException("La lista está vacia"));
                    } else {
                        return Flux.fromIterable(sales).map(saleMapper::entityToDtoRes);
                    }
                });
    }
}
