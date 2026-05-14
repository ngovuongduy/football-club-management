# football-club-management
# Đồ án: Hệ thống quản lý câu lạc bộ bóng đá

## 📌 Mô tả dự án
Hệ thống quản lý toàn diện cho một câu lạc bộ bóng đá, bao gồm:
- Quản lý cầu thủ (thông tin cá nhân, vị trí, số áo, chấn thương)
- Quản lý huấn luyện viên & ban huấn luyện
- Quản lý lịch tập luyện, thi đấu
- Quản lý kết quả & bảng xếp hạng (nếu có giải đấu)
- Quản lý chuyển nhượng / hợp đồng

## 🛠 Công nghệ sử dụng
| Thành phần | Công nghệ |
|------------|-----------|
| Frontend | React.js / HTML/CSS / Bootstrap |
| Backend | Node.js / Express (hoặc PHP, Java tùy bạn) |
| Database | MySQL / SQL Server |
| Quản lý phiên bản | Git + GitHub |

## 📅 Các mốc chính (Milestone)
| Mốc | Mô tả | Ngày |
|-----|-------|------|
| M1 | Khởi tạo dự án | 01/03 |
| M2 | Lập kế hoạch | 10/03 |
| M3 | Thiết kế CSDL + UI | 01/04 |
| M4 | Phát triển core features (CRUD cầu thủ, HLV) | 22/04 |
| M5 | Phát triển tính năng lịch thi đấu, kết quả | 06/05 |
| M6 | Kiểm thử, sửa lỗi, hoàn thiện | 06/05 – 12/05 |
| M7 | Demo lần 2 (Final) & Bàn giao | 12/05 |
| M8 | Nộp báo cáo đồ án | 15/05 |

## ✅ Tính năng chính (dạng checklist)
- [x] Đăng nhập phân quyền (Admin, HLV, thành viên CLB)
- [x] Quản lý cầu thủ (thêm, sửa, xóa, tìm kiếm)
- [x] Quản lý huấn luyện viên
- [x] Quản lý lịch tập luyện & thi đấu
- [ ] Cập nhật kết quả trận đấu (đang làm)
- [ ] Thống kê & báo cáo (số bàn thắng, thẻ phạt…)
- [ ] Xuất danh sách cầu thủ ra Excel/PDF

## 👥 Đối tượng sử dụng
- **Admin:** Quản lý toàn bộ hệ thống
- **Huấn luyện viên:** Xem lịch tập, điểm danh cầu thủ
- **Thành viên CLB:** Xem thông tin đội bóng, lịch thi đấu

## 🧪 Hướng dẫn cài đặt & chạy

### Yêu cầu:
- XAMPP / WAMP (nếu dùng PHP/MySQL)
- Hoặc Node.js + MySQL (nếu dùng MERN)

### Các bước:
```bash
# 1. Clone dự án
git clone https://github.com/ten-ban/quan-ly-clb-bong-da.git

# 2. Import database
- Mở phpMyAdmin
- Tạo database `clb_bongda`
- Import file `database/clb_bongda.sql`

# 3. Cấu hình kết nối
- Sửa file config (`.env` hoặc `config.php`) theo đúng user/password MySQL của bạn

# 4. Chạy ứng dụng
- Nếu dùng PHP: đặt thư mục vào `htdocs` và chạy Apache
- Nếu dùng Node: `npm install` + `npm start`
