package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.LoanResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoanCreateRequestModel;
import eric.koo.loan.management.system.entity.LoanEntity;
import eric.koo.loan.management.system.entity.LoanPaymentEntity;
import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.LoanPaymentService;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
class LoanController {

    private final LoanService loanService;
    private final LoanPaymentService loanPaymentService;

    @Autowired
    LoanController(LoanService loanService, LoanPaymentService loanPaymentService) {
        this.loanService = loanService;
        this.loanPaymentService = loanPaymentService;
    }

    @PostMapping
    LoanResponseModel createLoan(@Valid @RequestBody LoanCreateRequestModel loanCreateRequestModel, Principal principal) {
        var loan = loanService.createLoan(loanCreateRequestModel.getAmount(), loanCreateRequestModel.getType(), loanCreateRequestModel.getPaymentType(), principal.getName());

        return createLoanResponseModel(loan);
    }

    @PostMapping("/approve")
    LoanResponseModel approveLoan(@RequestParam("loanId") Long loanId, Principal principal) {
        var loan = loanService.approveLoan(loanId, principal.getName());

        return createLoanResponseModel(loan);
    }

    @GetMapping
    List<LoanResponseModel> findLoanByUsername(Principal principal) {
        var loans = loanService.findLoanByApplicantUsername(principal.getName());

        return loans.stream()
                .map(this::createLoanResponseModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    List<LoanResponseModel> findAllLoan() {
        var loans = loanService.findAllLoan();

        return loans.stream()
                .map(this::createLoanResponseModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{loanId}")
    LoanResponseModel getLoanByLoanId(@PathVariable("loanId") Long loanId, Authentication authentication) {
        var loan = loanService.getLoanByLoanId(loanId)
                .filter(loanEntity -> loanEntity.getCreditFacility().getApplicant().getUsername().equals(authentication.getName())
                        || authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(Role.BANK_STAFF.toString())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("No authority to access this loan information - %s", loanId)));

        return createLoanResponseModel(loan);
    }

    private LoanResponseModel createLoanResponseModel(LoanEntity loan) {
        var repaymentAmount = loan.getPrincipalAmount()
                .add(loan.getPrincipalAmount().multiply(loan.getInterestRate()))
                .setScale(2, RoundingMode.HALF_UP);
        var paidAmount = loanPaymentService.findLoanPaymentByLoanId(loan.getLoanId())
                .stream().map(LoanPaymentEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var remainingAmount = repaymentAmount.subtract(paidAmount);

        var loanResponse = new LoanResponseModel();
        BeanUtils.copyProperties(loan, loanResponse);
        loanResponse.setApplicantUsername(loan.getCreditFacility().getApplicant().getUsername());
        loanResponse.setPaidAmount(paidAmount);
        loanResponse.setRemainingAmount(remainingAmount);

        return loanResponse;
    }
}
