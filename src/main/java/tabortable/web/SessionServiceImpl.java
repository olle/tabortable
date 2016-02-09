package tabortable.web;

import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
class SessionServiceImpl implements SessionService {

	private final Supplier<HttpSession> httpSessionSupplier;

	public SessionServiceImpl() {
		this(() -> {
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest().getSession(true);
			return session;
		});
	}

	SessionServiceImpl(Supplier<HttpSession> httpSessionSupplier) {
		this.httpSessionSupplier = httpSessionSupplier;
	}

	@Override
	public void setSelectedTable(Optional<String> table) {

		table.ifPresent(t -> httpSessionSupplier.get().setAttribute("selected-table", t));
	}

}
