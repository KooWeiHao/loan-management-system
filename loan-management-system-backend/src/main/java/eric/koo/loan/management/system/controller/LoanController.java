package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.LoanResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoanCreateRequestModel;
import eric.koo.loan.management.system.entity.LoanEntity;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
class LoanController {

    private final LoanService loanService;

    @Autowired
    LoanController(LoanService loanService) {
        this.loanService = loanService;
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

    private LoanResponseModel createLoanResponseModel(LoanEntity loan) {
        var loanResponse = new LoanResponseModel();
        BeanUtils.copyProperties(loan, loanResponse);
        loanResponse.setApplicantUsername(loan.getCreditFacility().getApplicant().getUsername());

        return loanResponse;
    }
}
