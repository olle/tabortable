package tabortable.web;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceImplTest {

	@Mock
	Supplier<HttpSession> httpSessionSupplier;

	private SessionServiceImpl sessionServiceImpl;

	@Before
	public void setup() {
		sessionServiceImpl = new SessionServiceImpl(httpSessionSupplier);
	}

	@Test
	public void ensureSetSelectedTableIsAppliedOnSuppliedHttpSession() {

		HttpSession httpSession = Mockito.mock(HttpSession.class);
		when(httpSessionSupplier.get()).thenReturn(httpSession);

		sessionServiceImpl.setSelectedTable(Optional.of("some-table-name"));

		verify(httpSessionSupplier).get();
		verify(httpSession).setAttribute("selected-table", "some-table-name");
	}
	
	@Test
	public void ensureEmptyTableNameSkipsAnyInvokation() throws Exception {
		
		sessionServiceImpl.setSelectedTable(Optional.empty());
		
		verifyNoMoreInteractions(httpSessionSupplier);
		
	}

}
