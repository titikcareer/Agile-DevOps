namespace main.Services;

public static class Roles
{
    public const string DashboardAccess = "DashboardAccess";
}

public record UserAccount(string Nim, string Name, string Department, string PasswordHash, IReadOnlyList<string> Roles);

public class UserAccountStore
{
    private readonly object _lock = new();
    private readonly Dictionary<string, UserAccount> _usersByNim;
    private readonly Dictionary<string, (int FailedCount, DateTime LockedUntilUtc)> _loginAttempts = new();

    private const int MaxFailedAttempts = 5;
    private static readonly TimeSpan LockoutDuration = TimeSpan.FromMinutes(15);

    public UserAccountStore()
    {
        const string demoPassword = "ChangeMe123!";

        _usersByNim = new List<UserAccount>
        {
            new("011915", "Fajri Kurniawan", "GA, CSR, KM, SECURITY", PasswordHasher.Hash(demoPassword), new[] { Roles.DashboardAccess }),
            new("019900", "Suarsono", "GA, CSR, KM, SECURITY", PasswordHasher.Hash(demoPassword), new[] { Roles.DashboardAccess }),
            new("012044", "M. Amirudin", "GA, CSR, KM, SECURITY", PasswordHasher.Hash(demoPassword), Array.Empty<string>()),
            new("013210", "Sri Haryanto", "Digital Transformation", PasswordHasher.Hash(demoPassword), Array.Empty<string>()),
        }.ToDictionary(u => u.Nim, StringComparer.OrdinalIgnoreCase);
    }

    public UserAccount? TryAuthenticate(string nim, string password)
    {
        lock (_lock)
        {
            if (_loginAttempts.TryGetValue(nim, out var attempt) && attempt.LockedUntilUtc > DateTime.UtcNow)
            {
                return null;
            }

            if (!_usersByNim.TryGetValue(nim, out var user) || !PasswordHasher.Verify(password, user.PasswordHash))
            {
                RegisterFailedAttempt(nim);
                return null;
            }

            _loginAttempts.Remove(nim);
            return user;
        }
    }

    private void RegisterFailedAttempt(string nim)
    {
        var current = _loginAttempts.TryGetValue(nim, out var existing) ? existing.FailedCount : 0;
        var count = current + 1;
        var lockedUntil = count >= MaxFailedAttempts ? DateTime.UtcNow.Add(LockoutDuration) : DateTime.MinValue;
        _loginAttempts[nim] = (count, lockedUntil);
    }

    public UserAccount? FindByNim(string nim)
    {
        lock (_lock)
        {
            return _usersByNim.TryGetValue(nim, out var user) ? user : null;
        }
    }

    public bool ChangePassword(string nim, string currentPassword, string newPassword)
    {
        lock (_lock)
        {
            if (!_usersByNim.TryGetValue(nim, out var user))
            {
                return false;
            }

            if (!PasswordHasher.Verify(currentPassword, user.PasswordHash))
            {
                return false;
            }

            _usersByNim[nim] = user with { PasswordHash = PasswordHasher.Hash(newPassword) };
            return true;
        }
    }
}
